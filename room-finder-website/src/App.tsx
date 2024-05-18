// src/App.tsx
import React from 'react';
import MapContainer from './MapContainer';
import { useLoadScript } from '@react-google-maps/api';

export default function App() {

  const { isLoaded } = useLoadScript({
    googleMapsApiKey: "AIzaSyBO80gzLVSQFsM5NBYwzl4CwvSxzsvOatQ",
    //stiu ca nu e bine sa punem aici dar in caz ca doriti sa-l rulati local, puteti folosi cheia mea 
    libraries: ['places'],
  });

  if (!isLoaded) return <div>Loading...</div>;
  return <MapContainer />;
}
