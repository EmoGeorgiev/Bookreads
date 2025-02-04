import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import signupService from '../../services/signup'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [email, setEmail] = useState('')
    const navigate = useNavigate()
    
    const handleSignUp = async e => {
        e.preventDefault()

        const credentials = {
            username,
            password,
            email
        }
        
        try {
            const user = await signupService.signup(credentials)
            if (user !== null) {
                setUsername('')
                setPassword('')
                setEmail('')
                navigate('/login')
            }
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h1 className='m-5 text-5xl text-center font-bold'>Bookreads</h1>
            <h1 className='text-4xl text-center font-serif'>Create Account</h1>
            <div className='mt-16 flex flex-col items-center'>
                <form onSubmit={handleSignUp}>
                    <div className='flex flex-col'>
                        <label htmlFor='username' className='ml-5 font-semibold'>
                            Username
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='text'
                            id='username'
                            value={username}
                            name='username'
                            onChange={({ target }) => setUsername(target.value)}
                            placeholder='Username'
                        />
                    </div>
                    <div className='flex flex-col'>
                        <label htmlFor='password' className='ml-5 font-semibold'>
                            Password
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 hover:bg-gray-100 border border-gray-500 rounded-4xl' 
                            type='password'
                            id='password'
                            value={password}
                            name='password'
                            onChange={({ target }) => setPassword(target.value)}
                            placeholder='Password'
                        />
                    </div>
                    <div className='flex flex-col'>
                        <label htmlFor='email' className='ml-5 font-semibold'>
                            Email
                        </label>
                        <input
                            className='w-80 p-1.5 m-5 hover:bg-gray-100 border border-gray-500 rounded-4xl' 
                            type='email'
                            id='email'
                            value={email}
                            name='email'
                            onChange={({ target }) => setEmail(target.value)}
                            placeholder='Email'
                        />
                    </div>
                    <button className='w-80 p-1.5 m-5 hover:bg-neutral-700 bg-black text-white text-xl font-semibold rounded-4xl'>
                        Create account
                    </button>
                </form>
                <p className='text-lg text-center font-semibold'>
                    Already have an account? <Link to='/login' className='underline'>Log in</Link>
                </p>
            </div>
        </div>
    )
}

export default SignUpForm