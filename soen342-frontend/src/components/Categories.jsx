import React from 'react';

function Categories() {
  const categories = [
    { name: "Yoga", image: "/path-to-yoga-image.jpg" },
    { name: "Swimming", image: "/path-to-swimming-image.jpg" },
    { name: "Cooking", image: "/path-to-cooking-image.jpg" },
  ];

  return (
    <section className="py-16 bg-gray-100 w-full">
      <h3 className="text-3xl font-bold text-center mb-8">Lesson Categories</h3>
      <div className="container mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
        {categories.map((category, index) => (
          <div key={index} className="bg-white shadow-md rounded-lg overflow-hidden transform transition duration-300 hover:scale-105">
            <img src={category.image} alt={category.name} className="h-40 w-full object-cover" />
            <h4 className="text-center text-lg font-semibold p-4">{category.name}</h4>
          </div>
        ))}
      </div>
    </section>
  );
}

export default Categories;