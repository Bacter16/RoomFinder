import React, { useState, useMemo, useCallback, useRef } from 'react';
import {
    GoogleMap,
    LoadScript,
    Marker,
    DirectionsRenderer,
    Circle,
    MarkerClusterer
} from '@react-google-maps/api';
import Places from './places';
import "./styles/global.css";

type LatLngLiteral = google.maps.LatLngLiteral;
type DirectionsResult = google.maps.DirectionsResult;
type MapOptions = google.maps.MapOptions;

const MapContainer: React.FC = () => {

    const [hotel, setHotel] = useState<LatLngLiteral>();
    const [clickedPosition, setClickedPosition] = useState<LatLngLiteral | null>(null);
    const [distance, setDistance] = useState<number>(1);
    const [hotels, setHotels] = useState<any[]>([]);
    const [fetching, setFetching] = useState<boolean>(false);
    const [selectedHotel, setSelectedHotel] = useState<{ id: number, name: string } | null>(null);
    const [rooms, setRooms] = useState<any[]>([]);
    const [selectedRoom, setSelectedRoom] = useState<any | null>(null);
    const [startDate, setStartDate] = useState<string>('');
    const [endDate, setEndDate] = useState<string>('');



    const mapRef = useRef<GoogleMap>();
    const center = useMemo<LatLngLiteral>(() => ({ lat: 46.768657, lng: 23.589918 }), []);
    const options = useMemo<MapOptions>(() => ({
        disableDefaultUI: true,
        clickableIcons: false,
        gestureHandling: "none",
    }), []);
    const onLoad = useCallback((map: GoogleMap) => {
        mapRef.current = map;
    }, []);

    const handleMapClick = (event: google.maps.MapMouseEvent) => {
        if (event.latLng) {
            const clickedLatlng = {
                lat: event.latLng.lat(),
                lng: event.latLng.lng(),
            };
            console.log(clickedLatlng);
            setClickedPosition(clickedLatlng);
        }
    };

    const handleDistanceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(event.target.value);
        setDistance(isNaN(value) ? 1 : value);
    };

    const handleFetchHotels = async () => {
        if (clickedPosition) {
            setFetching(true);
            try {
                const url = `http://localhost:8080/api/hotels/m=${distance},latitude=${clickedPosition.lat},longitude=${clickedPosition.lng}`;
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    console.log(data);
                    setHotels(data);
                } else {
                    console.error('Failed to fetch hotels');
                }
            } catch (error) {
                console.error('Error fetching hotels:', error);
            } finally {
                setFetching(false);
            }
            setSelectedHotel(null);
        }
    };

    const fetchRooms = async (hotelId: number) => {
        try {
            const response = await fetch(`http://localhost:8080/api/rooms/hotel=${hotelId}`);
            if (response.ok) {
                const data = await response.json();
                console.log(data);
                setRooms(data);
            } else {
                console.error('Failed to fetch rooms');
            }
        } catch (error) {
            console.error('Error fetching rooms:', error);
        }
    };

    const fetchAvailability = async (hotelId: number, roomNumber: number, start: string, end: string) => {
        try {
            console.log(start, end)

            const startTime = start + ' 00:00:00';
            const endTime = end + ' 00:00:00';

            console.log(startTime, endTime)

            const userId = "5803f8e2-3d5a-42fa-a2dd-9dfd1b4bc4aa";
            const response = await fetch(`http://localhost:8080/api/reservation/hotel=${hotelId}/room=${roomNumber}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userId,
                    startDate: startTime,
                    endDate: endTime
                })
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Availability:', data);
            } else {
                console.error('Failed to check availability');
            }
        } catch (error) {
            console.error('Error checking availability:', error);
        }
    };

    return (
        <div className='container'>
            <div className='controls'>
                <h1>Controls</h1>
                {selectedHotel && (
                    <div>
                        <h2>{selectedHotel.name}</h2>
                        {rooms.length > 0 ? (
                            <ul>
                                {rooms.map((room, index) => (
                                    <li key={index} onClick={() => setSelectedRoom(room)}>
                                        Room {room.id.roomNumber}: {room.roomType}, Price: ${room.price}, Available: {room.available ? 'Yes' : 'No'}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No rooms available for this hotel.</p>
                        )}
                    </div>
                )}
                {selectedRoom && (
                    <div>
                        <h3>Room {selectedRoom.id.roomNumber}</h3>
                        <label>
                            Start Date:
                            <input
                                type="date"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                            />
                        </label>
                        <label>
                            End Date:
                            <input
                                type="date"
                                value={endDate}
                                onChange={(e) => setEndDate(e.target.value)}
                            />
                        </label>
                        <button onClick={() => selectedHotel && fetchAvailability(selectedHotel.id, selectedRoom.id.roomNumber, startDate, endDate)}>
                            Check Availability
                        </button>
                    </div>
                )}
                <Places
                    setHotel={(position) => {
                        setHotel(position);
                        mapRef.current?.panTo(position);
                    }}
                />
                <input
                    type="number"
                    value={distance}
                    onChange={handleDistanceChange}
                    placeholder="Enter distance (in km)"
                />
                <button onClick={handleFetchHotels} disabled={!clickedPosition || fetching}>
                    {fetching ? 'Fetching...' : 'Find'}
                </button>
            </div>
            <div className='map'>
                <GoogleMap
                    zoom={14}
                    center={center}
                    mapContainerClassName='map-container'
                    options={options}
                    onClick={handleMapClick}
                >
                    {clickedPosition && <Marker position={clickedPosition} />}
                    {hotels.map((hotel, index) => (
                        <Marker
                            key={index}
                            position={{ lat: hotel.latitude, lng: hotel.longitude }}
                            title={hotel.name}
                            onClick={() => {
                                setSelectedHotel({ id: hotel.id, name: hotel.name });
                                fetchRooms(hotel.id);
                            }}
                        />
                    ))}
                </GoogleMap>
            </div>
        </div>
    );



};
export default MapContainer;
