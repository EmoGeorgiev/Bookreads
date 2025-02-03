import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { Bookshelf } from '../util/Bookshelf'
import Book from './Book'
import BookForm from './BookForm'
import bookService from '../services/books'

const BookList = () => {
    const [books, setBooks] = useState([])
    const [bookshelf, setBookshelf] = useState(Bookshelf.ALL)
    const [isEdited, setIsEdited] = useState(false)
    const [editedBook, setEditedBook] = useState(null)
    const { userId } = useParams()

    useEffect(() => {
        const getBooks = async () => {
            try {
                const newBooks = await bookService.getBooksByUserId(userId)
                setBooks(newBooks)
            } catch (error) {
                console.log(error)
            }
        }

        getBooks()
    }, [userId])

    const updateBook = async newBook => {
        try {
            const updatedBook = await bookService.updateBook(newBook.id, newBook)
            setBooks(books.map(book => book.id === updatedBook.id ? updatedBook : book))     
        } catch (error) {
            console.log(error)
        }
    }

    const deleteBook = async id => {
        try {
            await bookService.deleteBook(id)
            setBooks(books.filter(book => book.id !== id))
        } catch (error) {
            console.log(error)
        }
    }
    
    const handleEdit = book => {
        setEditedBook(book)
        setIsEdited(!isEdited)
    }

    const calculateLength = (bookshelf) => {
        return books.filter(book => book.bookshelf === bookshelf).length
    }

    return (
        <>
            {!isEdited ?
                <div>
                    <div>
                        <button onClick={() => setBookshelf(Bookshelf.ALL)}>
                            All({books.length})
                        </button>
                    </div>
                    <div>
                        <button onClick={() => setBookshelf(Bookshelf.READ)}>
                            Read({calculateLength(Bookshelf.READ)})
                        </button>
                    </div>
                    <div>
                        <button onClick={() => setBookshelf(Bookshelf.CURRENTLY_READING)}>
                            Currently Reading({calculateLength(Bookshelf.CURRENTLY_READING)})
                        </button>
                    </div>
                    <div>
                        <button onClick={() => setBookshelf(Bookshelf.WANT_TO_READ)}>
                            Want To Read({calculateLength(Bookshelf.WANT_TO_READ)})
                        </button>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Page Count</th>
                                <th>Rating</th>
                                <th>Bookshelf</th>
                                <th>Review</th>
                                <th>Date Read</th>
                            </tr>
                        </thead>
                        <tbody>
                            {books
                                .filter(book => bookshelf === Bookshelf.ALL ? book : book.bookshelf === bookshelf)
                                .map(book => <Book key={book.id} 
                                                    book={book} 
                                                    userId={parseInt(userId)} 
                                                    handleEdit={handleEdit} 
                                                    deleteBook={deleteBook} />)}
                        </tbody>
                    </table>
                </div> :
                <div>
                     <BookForm book={editedBook} save={updateBook} />
                     <button onClick={() => handleEdit(null)}>Cancel</button>
                </div>
            }
        </>
    )
}

export default BookList