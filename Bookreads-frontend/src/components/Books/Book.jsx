import { useAuth } from '../Auth/AuthContext'

const Book = ({ book, userId, handleViewReview, handleEdit, deleteBook }) => {
    const { user } = useAuth()

    const handleDelete = () => {
        if (window.confirm('Are you sure you want to delete "' + book.title + '"?')) {
            deleteBook(book.id)
        }
    }
    
    return (
        <tr>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.title}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.author}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.pageCount}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.rating ? `${book.rating}/5` : ''}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.bookshelf.toLowerCase()}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-think'>{book.dateRead}</td>
            <td className='w-48 px-10 py-2 text-lg text-center font-semibold'>
                {(book.review && book.review.length > 0) ? 
                    <button className='w-24 p-1.5 mx-auto block bg-black text-white text-lg hover:bg-neutral-700 font-mono border rounded-4xl'
                            onClick={() => handleViewReview(book)}>
                        View
                    </button> : 
                    <div>
                        No Review
                    </div>}
            </td>
            
            {user.id === userId && 
                <td>
                    <button className='w-24 mx-2.5 p-1.5 bg-black text-white text-lg hover:bg-neutral-700 font-mono border rounded-4xl'
                            onClick={() => handleEdit(book)}>
                        Edit
                    </button>
                </td>}
            {user.id === userId &&
                <td>
                    <button className='w-24 mx-2.5 p-1.5 bg-red-700 text-white text-lg hover:bg-red-900 font-mono border rounded-4xl'
                            onClick={handleDelete}>
                        Delete
                    </button>
                </td>}
        </tr>
    )
}

export default Book