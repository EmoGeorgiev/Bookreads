import { useNavigate } from 'react-router-dom'
import { useAuth } from './AuthContext'
import BookForm from './BookForm'
import bookService from '../services/books'

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
            <h1>Add Book</h1>
            <BookForm book={{ 
                    'title': '', 
                    'author': '', 
                    'pageCount': '', 
                    'rating': '', 
                    'bookshelf': 'READ', 
                    'review': '', 
                    'dateRead': '' }} save={addBook} />
        </div>
    )
}

export default AddBook