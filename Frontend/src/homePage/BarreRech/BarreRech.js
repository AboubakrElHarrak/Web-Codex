import React, { Component } from 'react';
import './BarreRech.css';
export default function BarreRech() {
        return(
                <div className='box'>
                    <form >
                        <input type="text" placeholder="Search the codex" />
                        <input type="submit"  value="Search" />
                    </form>
                </div>
        )
}
