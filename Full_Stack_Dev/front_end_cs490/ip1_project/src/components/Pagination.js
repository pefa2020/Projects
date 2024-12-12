import React from 'react'
import './Pagination.css';

const Pagination = ({currentPage, totalPosts, postsPerPage, setCurrentPage}) => {
    
        const totalPages = Math.ceil(totalPosts / postsPerPage)
      
        const goToFirstPage = () => setCurrentPage(1)
        const goToPrevPage = () => setCurrentPage(currentPage - 1)
        const goToNextPage = () => setCurrentPage(currentPage + 1)
        const goToLastPage = () => setCurrentPage(totalPages)
      
        return (
          <div className="a_parent">
            <div>
            <button className="pagination" onClick={goToFirstPage} disabled={currentPage === 1}>
              First Page
            </button>
            </div>
            <div>
            <button className="pagination" onClick={goToPrevPage} disabled={currentPage === 1}>
              Prev Page
            </button>
            </div>
            <div>
            <button className="pagination" onClick={goToNextPage} disabled={currentPage === totalPages}>
              Next Page
            </button>
            </div>
            <div>
            <button className="pagination" onClick={goToLastPage} disabled={currentPage === totalPages}>
              Last Page
            </button>
            </div>
          </div>
        )
}

export default Pagination