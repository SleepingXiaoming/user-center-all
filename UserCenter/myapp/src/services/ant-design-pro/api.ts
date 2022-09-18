// @ts-ignore
/* eslint-disable */
// @ts-ignore
import request from '@/plugins/golbalRequest';

/** 获取当前的用户 GET /api/user/current */
export async function currentUser(options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.CurrentUser>>('/api/user/current', {
        method: 'GET',
        ...(options || {}),
    });
}

/** 退出登录接口 POST /api/user/outLogin */
export async function outLogin(options?: { [key: string]: any }) {
    // return request<API.BaseResponse<Record<string, any>>>('/api/user/outLogin', {
    return request<API.BaseResponse<number>>('/api/user/outLogin', {
        method: 'POST',
        ...(options || {}),
    });
}

/** 登录接口 POST /api/user/login */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.LoginResult>>('/api/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: body,
        ...(options || {}),
    });
}

/** 注册接口 POST /api/user/register */
export async function register(body: API.RegisterParams, options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.RegisterParams>>('/api/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: body,
        ...(options || {}),
    });
}

/** 搜索用户 GET /api/user/search */
export async function searchUsers(options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.CurrentUser[]>>('/api/user/search', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
        // data: body,
        ...(options || {}),
    });
}

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.NoticeIconList>>('/api/notices', {
        method: 'GET',
        ...(options || {}),
    });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
    params: {
        // query
        /** 当前的页码 */
        current?: number;
        /** 页面的容量 */
        pageSize?: number;
    },
    options?: { [key: string]: any },
) {
    return request<API.BaseResponse<API.RuleList>>('/api/rule', {
        method: 'GET',
        params: {
            ...params,
        },
        ...(options || {}),
    });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(body: API.CurrentUser, options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.RuleListItem>>('/api/user/rule', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        data: body,
        ...(options || {}),
    });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
    return request<API.BaseResponse<API.RuleListItem>>('/api/user/rule', {
        method: 'POST',
        ...(options || {}),
    });
}

/** 删除规则 DELETE /api/user/rule */
export async function removeRule(body: API.DeleteParams, options?: { [key: string]: any }) {
    alert("删除成功");
    return request<API.BaseResponse<Record<string, any>>>('/api/user/rule', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        data: body,
        ...(options || {}),
    });

}
