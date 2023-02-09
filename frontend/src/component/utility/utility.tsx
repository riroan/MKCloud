import URL from '../_config/config'
import { StatusCodes } from 'http-status-codes'

export default async function call(api: string, method: string, headers?: HeadersInit, request?: Object, json?: boolean, redirect = true) {
	const url = URL + api
	const credentials = 'include'
	let body = undefined
	if (request) {
		body = JSON.stringify(request)
	}
	if (!headers && json) {
		headers = new Headers({ 'content-type': 'application/json' })
	}
	return fetch(url, {
		method,
		credentials,
		body,
		headers,
	})
		.then(res => {
			const code = res.status
			if (code === StatusCodes.UNAUTHORIZED && redirect) {
				alert('로그인이 필요한 서비스입니다.')
				window.location.href = '/login'
			}
			return res
		})
		.catch(err => {
			console.log('error : ', err)
			return Promise.reject(err)
		})
}

export function convertFileSize(size: number) {
	if (size === undefined || size === null) return "0.00byte"
	let ix = 0
	const unit = ['byte', 'KB', 'MB', 'GB']
	while (size >= 1024) {
		size /= 1024
		ix++
		size = Math.round(size * 100) / 100
	}
	return size.toFixed(2) + unit[ix]
}

export function dateFormat(date: Date) {
	let month = date.getMonth() + 1
	let day = date.getDate()
	const hour = date.getHours()
	const minute = date.getMinutes()

	return date.getFullYear() + '/' + month + '/' + day + ' ' + hour + ':' + minute
}
