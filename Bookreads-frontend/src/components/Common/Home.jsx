import { useAuth } from '../Auth/AuthContext'

const Home = () => {
    const { user } = useAuth()

    return (
        <div>
            <h1 className='m-10 text-5xl text-center font-semibold'>Home</h1>
            <h3 className='text-3xl text-center font-mono'>Welcome, {user.username}, to Bookreads.</h3>
        </div>
    )
}

export default Home