import React from 'react'
import styles from './Header.module.scss'
import classnames from 'classnames/bind'
const cx = classnames.bind(styles)

type HeaderProps = {
    title?: string,
    className?: string
}

export default function Header({ title, className }: HeaderProps) {
    return (
		<div className={cx('header', className)}>
            <span className={ cx('title')}>{title}</span>
		</div>
	)
}
