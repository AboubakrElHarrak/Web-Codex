import React from 'react';
import Footer from '../footer/Footer';
import Navbar from '../navbar/Navbar';
import Form from './content/Form';

export default function FormPage() {
    const MenuItems = [
        {
            title:'',
            url: '',
        }
    ]
  return <div>
            <Navbar links={MenuItems} />
            <section className='bg-dark '>
                <div className='container text-start py-5' style={{height: "753px"}}>
                    <Form />
                </div>
            </section> 
           <Footer/>
        </div>;
}
