import { useState } from 'react'
import { Bookshelf } from '../util/Bookshelf'
import Book from './Book'

const testBooks = [
    {'id': 1, 'title': 'The Great Gatsby', 'author': 'F. Scott Fitzgerald', 'pageCount': 180, 'rating': 5, 'review': 'Great book!', 'dateRead': '2021-01-01', 'bookshelf': Bookshelf.READ},
    {'id': 2, 'title': 'To Kill a Mockingbird', 'author': 'Harper Lee', 'pageCount': 281, 'rating': 4, 'review': 'Good book!', 'dateRead': '2021-01-02', 'bookshelf': Bookshelf.READ},
    {'id': 3, 'title': '1984', 'author': 'George Orwell', 'pageCount': 328, 'rating': 3, 'review': 'Okay book!', 'dateRead': '2021-01-03', 'bookshelf': Bookshelf.CURRENTLY_READING},
    {'id': 4, 'title': 'Pride and Prejudice', 'author': 'Jane Austen', 'pageCount': 226, 'rating': 2, 'review': 'Bad book!', 'dateRead': '2021-01-04', 'bookshelf': Bookshelf.WANT_TO_READ},
    {'id': 5, 'title': 'The Catcher in the Rye', 'author': 'J.D. Salinger', 'pageCount': 230, 'rating': 1, 'review': 'Terrible book!', 'dateRead': '2021-01-05', 'bookshelf': Bookshelf.WANT_TO_READ}
]

const BookList = ({ userId }) => {
    const [books, setBooks] = useState(testBooks)
    const [bookshelf, setBookshelf] = useState(Bookshelf.ALL)

    const getBooks = async () => {

    }

    const updateBook = async updatedBook => {
        
    }

    const deleteBook = async id => {

    }
    
    const calculateLength = (bookshelf) => {
        return books.filter(book => book.bookshelf === bookshelf).length
    }

    return (
        <>
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
                                            userId={userId} 
                                            updateBook={updateBook} 
                                            deleteBook={deleteBook} />)}
                </tbody>
            </table>
        </>
    )
}

export default BookList