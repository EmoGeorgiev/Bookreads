import { useState } from 'react'
//import { useAuth } from './AuthContext'

const Book = ({ book, userId, updateBook, deleteBook }) => {
    const [title, setTitle] = useState(book.title)
    const [author, setAuthor] = useState(book.author)
    const [pageCount, setPageCount] = useState(book.pageCount)
    const [rating, setRating] = useState(book.rating)
    const [bookshelf, setBookshelf] = useState(book.bookshelf)
    const [review, setReview] = useState(book.review)
    const [dateRead, setDateRead] = useState(book.dateRead)
    
    const [isEdited, setIsEdited] = useState(false)

    //const { user } = useAuth()

    const handleEdit = () => {
        //setIsEdited(true)
    }

    const handleUpdate = () => {
        const updatedbBook = {}
        updateBook(updatedbBook)
    }

    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete "' + book.title + '"?')) {
            deleteBook(book.id)
        }
    }

    return (
        <tr>
            <td>{title}</td>
            <td>{author}</td>
            <td>{pageCount}</td>
            <td>{rating}</td>
            <td>{bookshelf}</td>
            <td>{review}</td>
            <td>{dateRead}</td>
            {userId === userId && 
                <td>
                    <button onClick={handleEdit}>
                        Edit
                    </button>
                </td>}
            {userId === userId &&
                <td>
                    <button onClick={handleDelete}>
                        Delete
                    </button>
                </td>}
        </tr>
    )
}

export default Book