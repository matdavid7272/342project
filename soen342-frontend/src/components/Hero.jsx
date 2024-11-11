import React from 'react';
import { Link } from 'react-router-dom';

function Hero() {
  return (
    <section className="bg-cover bg-center h-96 flex flex-col justify-center items-center text-white" style={{ backgroundImage: "url('/path-to-hero-background.jpg')" }}>
      <h2 className="text-4xl font-bold mb-4">Unlock Your Potential with the Best Lessons</h2>
      <p className="text-lg mb-6">Discover a variety of courses to enhance your skills.</p>
      <Link to="/lessons" className="bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded font-semibold">
        Explore Lessons
      </Link>
    </section>
  );
}

export default Hero;