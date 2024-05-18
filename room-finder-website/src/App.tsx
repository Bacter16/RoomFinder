// src/App.tsx
import React from 'react';
import MapContainer from './MapContainer';
import { useLoadScript } from '@react-google-maps/api';

export default function App() {

  const { isLoaded } = useLoadScript({
    googleMapsApiKey: process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY,
    libraries: ['places'],
  });

  if (!isLoaded) return <div>Loading...</div>;
  return <MapContainer />;
}
