import { useState } from 'react'

const ChangeField = ({ fieldName, fieldValue, fieldChange }) => {
    const [field, setField] = useState('')
    
    const handleFieldChange = e => {
        e.preventDefault()
        fieldChange(field)
        setField('')
    }

    return (
        <div>
            <h2 className='m-5 text-2xl text-center font-mono font-bold'>{fieldName} change</h2>
            <div className='flex flex-col items-center font-mono'>
                <p className='m-3 text-lg'>
                    Current {fieldName}: {fieldValue}
                </p>
                <input
                    className='w-48 m-3 p-1.5 text-center focus:bg-gray-50 hover:bg-gray-100 border border-gray-500 rounded-4xl'
                    type='text'
                    value={field}
                    name={fieldName}
                    onChange={({ target }) => setField(target.value)}
                    placeholder={fieldName}
                />
                <button className='w-48 m-3 p-1.5 bg-black text-white hover:bg-neutral-700 rounded-4xl' onClick={handleFieldChange}>
                    Change {fieldName}
                </button>
            </div>
        </div>
        )
}

export default ChangeField