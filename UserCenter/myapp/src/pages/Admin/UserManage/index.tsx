import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import {useRef} from 'react';
import {searchUsers, removeRule} from "@/services/ant-design-pro/api";
import {message} from "antd";
import {DEFAULT_AVATAR} from "@/constants";


/**
 * 删除节点
 * @param selectedRow
 */
const handleRemove = async (selectedRow: API.CurrentUser) => {
    const hide = message.loading('正在删除');
    if (!selectedRow) return true;

    try {
        await removeRule(selectedRow);
        hide();
        message.success('Deleted successfully and will refresh soon');
        return true;
    } catch (error) {
        hide();
        message.error('Delete failed, please try again');
        return false;
    }
};


const columns: ProColumns<API.CurrentUser>[] = [
    {
        // 列名与返回的字段相对应
        dataIndex: 'id',
        valueType: 'indexBorder',
        width: 48,
    },
    {
        title: '用户名',
        dataIndex: 'username',
        copyable: true,// 是否允许复制
        // ellipsis: true,// 是否允许缩略
    },
    {
        title: '学号',
        dataIndex: 'studentNumber',
        copyable: true,// 是否允许复制
    },
    {
        title: '用户账户',
        dataIndex: 'userAccount',
        copyable: true,
    },
    {
        title: '性别',
        dataIndex: 'gender',
        valueType: 'select',
        valueEnum: {
            0: {text: '男'},
            1: {text: '女'}
        }
    },
    {
        title: '电话',
        dataIndex: 'phone',
        copyable: true,
    },
    {
        title: '邮件',
        dataIndex: 'email',
        copyable: true,
    },
    {
        title: '状态',
        dataIndex: 'userStatus',
        valueType: 'select',
        valueEnum: {
            0: {text: '正常', color: 'green'},
            1: {text: '禁言', color: 'grey'},
            2: {text: '封号', color: 'red'}
        },
    },
    {
        title: '角色',
        dataIndex: 'userRole',
        valueType: 'select',
        valueEnum: {
            0: {text: '普通用户', status: 'Default'},
            1: {text: '管理员', status: 'Success'},
        }
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        valueType: 'dateTime',
        copyable: true,
    },
    {
        title: '操作',
        valueType: 'option',
        key: 'option',
        render: (text, record, _, action) => [
            <a href={record.avatarUrl ? record.avatarUrl : DEFAULT_AVATAR} target="_blank" rel="noopener noreferrer"
               key="view">
                查看头像
            </a>,
            <a
                onClick={async () => {
                    await handleRemove(record);
                }}
            >
                删除
            </a>,
            // <a onClick={() => handleDelete(record)}> 删除 </a>,
        ],
    },
];


export default () => {
    const actionRef = useRef<ActionType>();
    return (
            <ProTable<API.CurrentUser>
                columns={columns}
                actionRef={actionRef}
                cardBordered
                // 该接口的返回值会自动将值填充到窗口中对应的值
                request={async (params = {}, sort, filter) => {
                    console.log(sort, filter);
                    const userList = await searchUsers(params);
                    return {data: userList}
                }}
                editable={{
                    type: 'multiple',
                }}
                columnsState={{
                    persistenceKey: 'pro-table-singe-demos',
                    persistenceType: 'localStorage',
                    onChange(value) {
                        console.log('value: ', value);
                    },
                }}
                rowKey="id"
                search={{
                    labelWidth: 'auto',
                }}
                options={{
                    setting: {
                        // listsHeight: 400,
                    },
                }}
                form={{
                    // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
                    syncToUrl: (values, type) => {
                        if (type === 'get') {
                            return {
                                ...values,
                                created_at: [values.startTime, values.endTime],
                            };
                        }
                        return values;
                    },
                }}
                pagination={{
                    pageSize: 5,
                    onChange: (page) => console.log(page),
                }}
                dateFormatter="string"
                headerTitle="高级表格"
            />
    );
};
