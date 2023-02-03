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
				window.location.href = '/login'
			}
			return res
		})
		.catch(err => {
			console.log('error : ', err)
			return Promise.reject(err)
		})
}
