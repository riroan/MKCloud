import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import LoginPage from './component/page/login/LoginPage'
import MainPage from './component/page/main/MainPage'
import RegisterPage from './component/page/register/RegisterPage'
import UserPage from './component/page/user/UserPage'

export default function App() {
	return (
		<div className="App">
			<Router>
				<Routes>
					<Route path="/login" element={<LoginPage />} />
					<Route path="/register" element={<RegisterPage />} />
					<Route path="/" element={<MainPage />} />
					<Route path="/user" element={<UserPage />} />
				</Routes>
			</Router>
		</div>
	)
}
