import React from 'react';

function Footer() {
  return (
    <footer className="bg-gray-900 text-white py-4 text-center w-full">
      <p>&copy; 2024 LessonHub. All Rights Reserved.</p>
      <div className="flex justify-center space-x-4 mt-2">
        <a href="#" className="hover:text-gray-300">Privacy Policy</a>
        <a href="#" className="hover:text-gray-300">Terms of Service</a>
      </div>
    </footer>
  );
}

export default Footer;

