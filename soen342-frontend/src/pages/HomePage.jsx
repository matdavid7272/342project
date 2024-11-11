import React from 'react';
import Navbar from '../components/Navbar';
import Hero from '../components/Hero';
import Categories from '../components/Categories';
import Footer from '../components/Footer';

function HomePage() {
    return (
    <div>
      <Navbar />
      <Hero />
      <Categories />
      <Footer />
    </div>
    );
}

export default HomePage;