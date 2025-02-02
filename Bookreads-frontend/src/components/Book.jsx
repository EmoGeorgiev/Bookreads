import { useState } from 'react'
import { useAuth } from './AuthContext'

const Book = ({ book, userId, updateBook, deleteBook }) => {
    const [isEdited, setIsEdited] = useState(false)
    const { user } = useAuth()

    const handleEdit = () => {
        setIsEdited(!isEdited)
    }

    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete "' + book.title + '"?')) {
            deleteBook(book.id)
        }
    }

    return (
        <tr>
            <td>{book.title}</td>
            <td>{book.author}</td>
            <td>{book.pageCount}</td>
            <td>{book.rating}</td>
            <td>{book.bookshelf}</td>
            <td>{book.review}</td>
            <td>{book.dateRead}</td>
            {user.id === userId && 
                <td>
                    <button onClick={handleEdit}>
                        Edit
                    </button>
                </td>}
            {user.id === userId &&
                <td>
                    <button onClick={handleDelete}>
                        Delete
                    </button>
                </td>}
        </tr>
    )
}

export default Book