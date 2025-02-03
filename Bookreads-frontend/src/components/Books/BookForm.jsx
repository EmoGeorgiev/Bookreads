import { useState } from 'react'
import { useAuth } from '../Auth/AuthContext'
import { Bookshelf } from '../../util/Bookshelf'

const BookForm = ({ book, save }) => {
    const [title, setTitle] = useState(book.title)
    const [author, setAuthor] = useState(book.author)
    const [pageCount, setPageCount] = useState(book.pageCount)
    const [rating, setRating] = useState(book.rating)
    const [bookshelf, setBookshelf] = useState(book.bookshelf)
    const [review, setReview] = useState(book.review)
    const [dateRead, setDateRead] = useState(book.dateRead)
    const { user } = useAuth()

    const handleSave = e => {
        e.preventDefault()

        const newBook = {
            ...book,
            title,
            author,
            pageCount,
            rating,
            bookshelf,
            review,
            dateRead,
            'userId': user.id
        }
        save(newBook)
    }

    return (
        <div>
            <form onSubmit={handleSave}>
                <div>
                    <p>Title:</p>
                    <input
                        type='text'
                        value={title}
                        onChange={({ target }) => setTitle(target.value)}
                        placeholder={title}
                    />
                </div>
                <div>
                    <p>Author:</p>
                    <input
                        type='text'
                        value={author}
                        onChange={({ target }) => setAuthor(target.value)}
                        placeholder={author}
                    />
                </div>
                <div>
                    <p>Page Count:</p>
                    <input
                        type='number'
                        min={1}
                        value={pageCount}
                        onChange={({ target }) => setPageCount(target.value)}
                        placeholder={pageCount}
                    />
                </div>
                <div>
                    <p>Rating:</p>
                    <select name='rating' value={rating || ''} onChange={({ target }) => setRating(target.value)} placeholder={rating}>
                        <option value=''></option>
                        <option value='5'>5</option>
                        <option value='4'>4</option>
                        <option value='3'>3</option>
                        <option value='2'>2</option>
                        <option value='1'>1</option>
                    </select>
                </div>
                <div>
                    <p>Bookshelf:</p>
                    <select name='bookshelf' value={bookshelf} onChange={({ target }) => setBookshelf(target.value)} placeholder={bookshelf}>
                        <option value={Bookshelf.READ}>{Bookshelf.READ.toLocaleLowerCase()}</option>
                        <option value={Bookshelf.CURRENTLY_READING}>{Bookshelf.CURRENTLY_READING.toLocaleLowerCase()}</option>
                        <option value={Bookshelf.WANT_TO_READ}>{Bookshelf.WANT_TO_READ.toLocaleLowerCase()}</option>
                    </select>
                </div>
                <div>
                    <p>Review:</p>
                    <textarea value={review || ''} onChange={({ target }) => setReview(target.value)}  placeholder={review}>

                    </textarea>
                </div>
                <div>
                    <p>Date Read:</p>
                    <input
                        type='date'
                        value={dateRead || ''}
                        onChange={({ target }) => setDateRead(target.value)}
                        placeholder={dateRead}
                    />
                </div>
                <div>
                    <button>
                        Save
                    </button>
                </div>
            </form>
        </div>
    )
}

export default BookForm