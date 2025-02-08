import { useError } from './ErrorContext'

const Error = () => {
    const { error } = useError()

    if (!error) {
        return null
    }

    return (
        <div className='m-5 text-red-700 text-center text-lg font-mono'>
            {error}
        </div>
    )
}

export default Error