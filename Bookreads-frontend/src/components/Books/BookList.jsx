import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { Bookshelf } from '../../util/Bookshelf'
import BookForm from './BookForm'
import BookTable from './BookTable'
import BookshelfCategory from './BookshelfCategory'
import bookService from '../../services/books'

const BookList = () => {
    const [books, setBooks] = useState([])
    const [filteredBooks, setFilteredBooks] = useState([])
    const [bookshelf, setBookshelf] = useState(Bookshelf.ALL)
    const [isEdited, setIsEdited] = useState(false)
    const [editedBook, setEditedBook] = useState(null)
    const { userId } = useParams()

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
            setIsEdited(false)    
        } catch (error) {
            console.log(error)
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
        setEditedBook(book)
        setIsEdited(!isEdited)
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

    return (
        <div className='mt-5'>
            {!isEdited ?
                <div>
                    <div className='flex justify-center'>
                        <BookshelfCategory name={`All(${books.length})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.ALL} handleBookshelf={handleBookshelf} />
                        <BookshelfCategory name={`Read(${calculateLength(Bookshelf.READ)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.READ} handleBookshelf={handleBookshelf} />
                        <BookshelfCategory name={`Currently Reading(${calculateLength(Bookshelf.CURRENTLY_READING)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.CURRENTLY_READING} handleBookshelf={handleBookshelf} />
                        <BookshelfCategory name={`Want To Read(${calculateLength(Bookshelf.WANT_TO_READ)})`} currentBookshelf={bookshelf} bookshelf={Bookshelf.WANT_TO_READ} handleBookshelf={handleBookshelf} />
                    </div>
                    <BookTable books={filteredBooks} bookshelf={bookshelf} userId={userId} handleEdit={handleEdit} deleteBook={deleteBook}/>
                </div> :
                <div>
                    
                    <h1 className='m-10 text-4xl text-center font-semibold'>Edit Book</h1>
                    <BookForm book={editedBook} save={updateBook} handleCancel={() => handleEdit(null)} />
                </div>}
        </div>
    )
}

export default BookList