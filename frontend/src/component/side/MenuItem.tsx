import React from 'react'
import styles from './MenuItem.module.scss'
import classnames from 'classnames/bind'
import { SvgIconComponent } from '@material-ui/icons'
const cx = classnames.bind(styles)

type MenuItemProps = {
	content?: string
	className?: string
	icon?: SvgIconComponent
}

export default function MenuItem({ content, className, icon }: MenuItemProps) {
	return (
		<div className={cx(className, 'container')}>
			<div className={cx('icon')}>{icon}</div>
			<div className={cx('content')}>{content}</div>
		</div>
	)
}
