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
            <section class='bg-dark '>
                <div className='container text-start py-5' style={{height: "653px"}}>
                    <Form />
                </div>
            </section> 
           <Footer/>
        </div>;
}
