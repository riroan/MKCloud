import { FormEvent, useState } from 'react'
import Avatar from '@mui/material/Avatar'
import Button from '@mui/material/Button'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import FormControlLabel from '@mui/material/FormControlLabel'
import Checkbox from '@mui/material/Checkbox'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import styles from './LoginPage.module.scss'
import classnames from 'classnames/bind'
import call from '../../utility/utility'
import { StatusCodes } from 'http-status-codes'
import crypto from 'crypto-js'
const cx = classnames.bind(styles)

function Copyright(props: any) {
	return (
		<Typography variant="body2" color="text.secondary" align="center" {...props}>
			{'Copyright © '}
			<Link color="inherit" href="https://mui.com/">
				Your Website
			</Link>{' '}
			{new Date().getFullYear()}
			{'.'}
		</Typography>
	)
}

const theme = createTheme()

export default function SignIn() {
	const [idError, setIdError] = useState(false)
	const [passwordError, setPasswordError] = useState(false)
	const [idErrorMessage, setIdErrorMessage] = useState('')
	const [passwordErrorMessage, setPasswordErrorMessage] = useState('')
	const [loginError, setLoginError] = useState(false)

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault()
		const data = new FormData(event.currentTarget)
		const id = data.get('id')
		const password = data.get('password')
		let valid = true
		setLoginError(false)
		setIdError(false)
		setPasswordError(false)
		if (!id) {
			setIdError(true)
			setIdErrorMessage('아이디가 비어있습니다!')
			valid = false
		}
		if (!password) {
			setPasswordError(true)
			setPasswordErrorMessage('비밀번호가 비어있습니다!')
			valid = false
		}
		if (valid) {
			const pw = crypto.SHA512(password!.toString()).toString()
			const body = { id, password: pw }
			call('/login', 'POST', undefined, body, true, false).then(res => {
				if (res.status === StatusCodes.UNAUTHORIZED) {
					setIdError(true)
					setPasswordError(true)
					setIdErrorMessage('')
					setPasswordErrorMessage('')
					setLoginError(true)
					return
				}
				if (res.status === StatusCodes.BAD_REQUEST) {
					alert('관리자 승인 대기중인 아이디입니다.')
					return
				}
				window.location.href = '/'
			})
		}
	}

	return (
		<ThemeProvider theme={theme}>
			<Container component="main" maxWidth="xs">
				<CssBaseline />
				<Box
					sx={{
						marginTop: 8,
						display: 'flex',
						flexDirection: 'column',
						alignItems: 'center',
					}}
				>
					<Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
						<LockOutlinedIcon />
					</Avatar>
					<Typography component="h1" variant="h5">
						로그인
					</Typography>
					<Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
						<TextField error={idError} margin="normal" required fullWidth id="id" label="아이디" name="id" autoFocus />
						{idError && <span className={cx('error')}>{idErrorMessage}</span>}
						<TextField error={passwordError} margin="normal" required fullWidth name="password" label="비밀번호" type="password" id="password" />
						{passwordError && <span className={cx('error')}>{passwordErrorMessage}</span>}
						<br />
						{loginError && <span className={cx('error')}>로그인 정보가 일치하지 않습니다.</span>}
						<br />
						<Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
							로그인
						</Button>
						<Grid container justifyContent="flex-end">
							<Grid item>
								<Link href="/register" variant="body2">
									아이디가 없으십니까?
								</Link>
							</Grid>
						</Grid>
					</Box>
				</Box>
				<Copyright sx={{ mt: 8, mb: 4 }} />
			</Container>
		</ThemeProvider>
	)
}
