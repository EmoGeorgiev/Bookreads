const clickedSettingStyle = 'w-52 m-1 p-1.5 bg-black text-white text-lg font-mono rounded-4xl'
const notClickedSettingStyle = 'w-52 m-1 p-1.5 bg-white text-black text-lg font-mono border border border-gray-500 rounded-4xl'

const SettingButton = ({ currentSetting, setting, handleSettingChange }) => {
    return (
        <button className={currentSetting === setting ? clickedSettingStyle : notClickedSettingStyle}
            onClick={() => handleSettingChange(setting)}>
            {setting}
        </button>
    )
}

export default SettingButton