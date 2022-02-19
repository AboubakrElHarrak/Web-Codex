import React, { Component } from 'react';
import './BarreRech.css';
export default function BarreRech() {
        return(
                <div className='box'>
                    <form action="/" method="get" >
                        <input type="text" placeholder="Search the codex" name="search" />
                        <input type="submit"  value="Search" />
                    </form>
                </div>
        )
}
