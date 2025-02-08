import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Bookshelf } from '../../util/Bookshelf'
import BookForm from './BookForm'
import BookReview from './BookReview'
import BookTable from './BookTable'
import BookshelfCategory from './BookshelfCategory'
import bookService from '../../services/books'
import { defaultBookErrors } from '../../util/Errors'
import { useAuth } from '../Auth/AuthContext'

const BookList = () => {
    const [books, setBooks] = useState([])
    const [currentBook, setCurrentBook] = useState(null)
    const [filteredBooks, setFilteredBooks] = useState([])
    const [bookshelf, setBookshelf] = useState(Bookshelf.ALL)
    const [showEdited, setShowEdited] = useState(false)
    const [showReview, setShowReview] = useState(false)
    const [errors, setErrors] = useState(defaultBookErrors)
   
    const { userId } = useParams()
    const id = parseInt(userId)
    const { user } = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        const getBooks = async () => {
            try {
                const newBooks = await bookService.getBooksByUserId(userId)
                setBooks(newBooks)
                setFilteredBooks(newBooks)
            } catch (error) {
                console.log(error)
            }
        }

        getBooks()
    }, [userId])

    const updateBook = async newBook => {
        try {
            const updatedBook = await bookService.updateBook(newBook.id, newBook)
            const newBooks = books.map(book => book.id === updatedBook.id ? updatedBook : book)
            setBooks(newBooks)
            handleFilteredBooks(newBooks, bookshelf)
            setShowEdited(false)    
        } catch (error) {
            setErrors({ ...errors, ...error.response.data})
            setTimeout(() => {
                setErrors(defaultBookErrors)
            }, 3000)
        }
    }

    const deleteBook = async id => {
        try {
            await bookService.deleteBook(id)
            const newBooks = books.filter(book => book.id !== id)
            setBooks(newBooks)
            handleFilteredBooks(newBooks, bookshelf)
        } catch (error) {
            console.log(error)
        }
    }
    
    const handleEdit = book => {
        setCurrentBook(book)
        setShowEdited(!showEdited)
    }

    const handleViewReview = book => {
        setCurrentBook(book)
        setShowReview(!showReview)
    }

    const calculateLength = bookshelf => {
        return books.filter(book => book.bookshelf === bookshelf).length
    }
    
    const handleFilteredBooks = (newBooks, currentBookshelf) => {
        setFilteredBooks(newBooks.filter(book => currentBookshelf === Bookshelf.ALL ? book : book.bookshelf === currentBookshelf))
    }

    const handleBookshelf = newBookshelf => {
        setBookshelf(newBookshelf)
        handleFilteredBooks(books, newBookshelf)
    }

    if (showEdited) {
        return (
            <div>
                <h1 className='m-10 text-4xl text-center font-semibold'>Edit Book</h1>
                <BookForm book={currentBook} save={updateBook} handleCancel={() => handleEdit(null)} errors={errors} />
            </div>  
        )
    } else if (showReview) {
        return (
            <div>
                <BookReview book={currentBook} handleViewReview={handleViewReview}/>
            </div>
        )
    } else {
        return (
            <div>
                <div className='mt-10 flex justify-center'>
                    <BookshelfCategory name={`All(${books.length})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.ALL} handleBookshelf={handleBookshelf} />
                    <BookshelfCategory name={`Read(${calculateLength(Bookshelf.READ)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.READ} handleBookshelf={handleBookshelf} />
                    <BookshelfCategory name={`Currently Reading(${calculateLength(Bookshelf.CURRENTLY_READING)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.CURRENTLY_READING} handleBookshelf={handleBookshelf} />
                    <BookshelfCategory name={`Want To Read(${calculateLength(Bookshelf.WANT_TO_READ)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.WANT_TO_READ} handleBookshelf={handleBookshelf} />
                </div>
                {user.id === id && 
                    <button className='w-32 p-1.5 mt-10 mx-auto block bg-black text-white text-lg hover:bg-neutral-700 font-mono border rounded-4xl'
                            onClick={() => navigate('/books/add')}>
                        Add Book
                    </button>}
                <BookTable books={filteredBooks} bookshelf={bookshelf} userId={id} handleViewReview={handleViewReview} handleEdit={handleEdit} deleteBook={deleteBook}/>
            </div>
        )
    }
}

export default BookList