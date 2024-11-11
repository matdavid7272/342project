import React from 'react';
import { Link } from 'react-router-dom';

function Navbar() {
  return (
<nav className="bg-gray-800 fixed top-0 w-full z-50 m-0">
  <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
    <div className="relative flex h-16 items-center justify-between">
      <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
        <button type="button" className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white" aria-controls="mobile-menu" aria-expanded="false">
          <span className="absolute -inset-0.5"></span>
          <span className="sr-only">Open main menu</span>
          <svg className="block h-6 w-6" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
            <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
          </svg>
        </button>
      </div>
      <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
        <div className="flex shrink-0 items-center">
          <h1 className="text-2xl font-bold text-white">LessonHub</h1>
        </div>
        <div className="hidden sm:ml-6 sm:block">
          <div className="flex space-x-4">
            <Link to="/" className="text-white hover:bg-gray-700 px-3 py-2 text-sm font-medium">Home</Link>
            <Link to="/lessons" className="text-white hover:bg-gray-700 px-3 py-2 text-sm font-medium">Lessons</Link>
            <Link to="/about" className="text-white hover:bg-gray-700 px-3 py-2 text-sm font-medium">About Us</Link>
            <Link to="/contact" className="text-white hover:bg-gray-700 px-3 py-2 text-sm font-medium">Contact</Link>
            <Link to="/login" className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded">Login</Link>
            <Link to="/signup" className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded">Sign Up</Link>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div className="sm:hidden" id="mobile-menu">
    <div className="space-y-1 px-2 pb-3 pt-2">
      <Link to="/" className="block rounded-md bg-gray-900 text-white px-3 py-2 text-base font-medium">Home</Link>
      <Link to="/lessons" className="block rounded-md text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 text-base font-medium">Lessons</Link>
      <Link to="/about" className="block rounded-md text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 text-base font-medium">About Us</Link>
      <Link to="/contact" className="block rounded-md text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 text-base font-medium">Contact</Link>
      <Link to="/login" className="block rounded-md bg-blue-600 hover:bg-blue-700 text-white px-3 py-2 text-base font-medium">Login</Link>
      <Link to="/signup" className="block rounded-md bg-green-600 hover:bg-green-700 text-white px-3 py-2 text-base font-medium">Sign Up</Link>
    </div>
  </div>
</nav>

  );
}

export default Navbar;