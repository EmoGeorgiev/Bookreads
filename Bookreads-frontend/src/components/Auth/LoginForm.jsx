import { useState } from 'react'
import { useNavigate} from 'react-router-dom'
import { useAuth } from './AuthContext'
import Error from '../Error/Error'
import { useError } from '../Error/ErrorContext'
import loginService from '../../services/login'
 
const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()
    const { triggerError } = useError()
    const { login } = useAuth()

    const handleLogin = async e => {
        e.preventDefault()

        try {
            const data = await loginService.login({
                username,
                password
            })
            login(data)
            setUsername('')
            setPassword('')
            navigate('/')
        } catch (error) {
            triggerError('Wrong username or password')
        }
    }

    return (
        <div>
            <h1 className='m-5 text-5xl text-center font-bold'>Bookreads</h1>
            <h1 className='text-4xl text-center font-serif'>Log in</h1>
            <div className='mt-16 flex flex-col items-center'>
                <form onSubmit={handleLogin}>
                    <div className='flex flex-col text-center'>
                        <label htmlFor='username' className='ml-5 text-center font-semibold'>
                            Username
                        </label>
                        <input 
                            className='w-80 p-1.5 m-5 text-center hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='text'
                            id='username'
                            value={username}
                             name='username'
                            onChange={({ target }) => setUsername(target.value)}
                        />
                    </div>
                    <div className='flex flex-col'>
                        <label htmlFor='password' className='ml-5 text-center font-semibold'>
                            Password
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 text-center hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='password'
                            id='password'
                            value={password}
                            name='password'
                            onChange={({ target }) => setPassword(target.value)}
                        />
                    </div>
                    <button className='w-80 p-1.5 m-5 hover:bg-neutral-700 bg-black text-white text-xl font-semibold rounded-4xl'>
                        Log in
                    </button>
                    <Error />
                </form>
                <div>
                    <p className='text-lg text-center font-semibold'>
                        New to Bookreads?
                    </p>
                    <button className='w-80 p-1.5 m-5 hover:bg-gray-200 border text-xl font-semibold rounded-4xl'
                            onClick={() => navigate('/signup')}>
                        Sign up
                    </button>
                </div>
            </div>
        </div>
    )
}

export default LoginForm    