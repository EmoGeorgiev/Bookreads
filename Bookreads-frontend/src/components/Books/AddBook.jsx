import { useNavigate } from 'react-router-dom'
import { useAuth } from '../Auth/AuthContext'
import BookForm from './BookForm'
import bookService from '../../services/books'

const AddBook = () => {
    const { user } = useAuth()
    const navigate = useNavigate()

    const addBook = async book => {
        try {
            await bookService.addBook(book)
            navigate(`/users/${user.id}/books`)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h1 className='m-10 text-4xl text-center font-semibold'>Add Book</h1>
            <BookForm book={{ 
                    'title': '', 
                    'author': '', 
                    'pageCount': '', 
                    'rating': '', 
                    'bookshelf': 'READ', 
                    'review': '', 
                    'dateRead': '' }} save={addBook} handleCancel={() => window.location.reload()} />
        </div>
    )
}

export default AddBook