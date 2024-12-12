import React from 'react'
import "./Header.css"
import { Link } from "react-router-dom";

function Navbar() {
  return (
    <div className="menu">
        <ul className="navbar_menu">
            <li><Link to="/">HOME</Link></li>
            <li><Link to="/FilmPage">FILMS</Link></li>
            <li><Link to="/CustomerPage">CUSTOMERS</Link></li>
        </ul>
    </div>
  )
}

export default Navbar