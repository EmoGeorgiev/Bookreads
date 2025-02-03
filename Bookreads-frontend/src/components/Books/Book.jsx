import { useAuth } from '../Auth/AuthContext'

const Book = ({ book, userId, handleEdit, deleteBook }) => {
    const { user } = useAuth()

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
            <td>{book.rating ? `${book.rating}/5` : ''}</td>
            <td>{book.bookshelf.toLowerCase()}</td>
            <td>{book.review}</td>
            <td>{book.dateRead}</td>
            {user.id === userId && 
                <td>
                    <button onClick={() => handleEdit(book)}>
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