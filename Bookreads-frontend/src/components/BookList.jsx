import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { Bookshelf } from '../util/Bookshelf'
import BookForm from './BookForm'
import BookTable from './BookTable'
import BookshelfCategory from './BookshelfCategory'
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
            setIsEdited(false)    
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

    const calculateLength = bookshelf => {
        return books.filter(book => book.bookshelf === bookshelf).length
    }

    return (
        <>
            {!isEdited ?
                <div>
                    <BookshelfCategory 
                        name={`All(${books.length})`}
                        handleClick={() => setBookshelf(Bookshelf.ALL)}
                    />
                    <BookshelfCategory
                        name={`Read(${calculateLength(Bookshelf.READ)})`} 
                        handleClick={() => setBookshelf(Bookshelf.READ)}
                    />
                    <BookshelfCategory
                        name={`Currently Reading(${calculateLength(Bookshelf.CURRENTLY_READING)})`} 
                        handleClick={() => setBookshelf(Bookshelf.CURRENTLY_READING)}
                    />
                    <BookshelfCategory
                        name={`Want To Read(${calculateLength(Bookshelf.WANT_TO_READ)})`} 
                        handleClick={() => setBookshelf(Bookshelf.WANT_TO_READ)}
                    />
                    
                    <BookTable 
                        books={books}
                        bookshelf={bookshelf} 
                        userId={userId} 
                        handleEdit={handleEdit} 
                        deleteBook={deleteBook}
                    />
                </div> :
                <div>
                    <h1>Edit Book</h1>
                    <BookForm book={editedBook} save={updateBook} />
                    <button onClick={() => handleEdit(null)}>Cancel</button>
                </div>}
        </>
    )
}

export default BookList