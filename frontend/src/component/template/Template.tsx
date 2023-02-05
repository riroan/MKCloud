import React from 'react'
import Header from '../header/Header'
import SideMenu from '../side/SideMenu'
import styles from './Template.module.scss'
import classnames from 'classnames/bind'
const cx = classnames.bind(styles)

type TemplateProps = {
	children?: React.ReactNode
	className?: string
	title?: string
}

export default function Template({ children, className, title }: TemplateProps) {
	return (
		<div className={className}>
			<Header title={title} />
			<div className={cx('container')}>
				<SideMenu className={cx('side')} />
				<div className={ cx('main')}>{children}</div>
			</div>
		</div>
	)
}
