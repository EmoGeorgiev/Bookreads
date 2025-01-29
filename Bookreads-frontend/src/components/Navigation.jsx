import { Link } from 'react-router-dom'

const Navigation = () => {
    return (
        <>
            <nav>
                <Link to='/'>Home</Link>
                <Link to='/books'>My Books</Link>
                <Link to='/users'>Users</Link>
                <Link to='/settings'>Settings</Link>
            </nav>
        </>
    )
}

export default Navigation