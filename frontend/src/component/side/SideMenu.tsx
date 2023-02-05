import React from 'react'
import styles from './SideMenu.module.scss'
import classnames from 'classnames/bind'
import MenuItem from './MenuItem'
import StorageIcon from '@mui/icons-material/Storage'
import PersonIcon from '@mui/icons-material/Person'
import { Link } from 'react-router-dom'
import Button from '@mui/material/Button'
import { Box } from '@mui/material'
import call from '../utility/utility'
const cx = classnames.bind(styles)

type SideMenuProps = {
	className?: string
}

export default function SideMenu({ className }: SideMenuProps) {
	const MenuElement = [{ name: 'Drive', icon: <StorageIcon />, link: '/' }]
	const UserElement = [{ name: 'My Page', icon: <PersonIcon />, link: '/user' }]
	const handleLogout = () => {
		call('/logout', 'POST', undefined, undefined, undefined, true)
			.then(res=>window.location.href="/login")
	}
	return (
		<div className={cx(className, 'container')}>
			<div className={cx('service')}>Service</div>
			{MenuElement.map((value, ix) => (
				<Link key={ix} to={value.link}>
					<MenuItem content={value.name} icon={value.icon} />
				</Link>
			))}
			<hr className={cx('border')} />
			<div className={cx('service')}>User</div>
			{UserElement.map((value, ix) => (
				<Link key={ix} to={value.link}>
					<MenuItem content={value.name} icon={value.icon} />
				</Link>
			))}
			<Box className={cx('box')} textAlign="center">
				<Button onClick={handleLogout} className={cx('logout')} variant="contained" color="error">
					<span className={ cx('text')}>Logout</span>
				</Button>
			</Box>
		</div>
	)
}
