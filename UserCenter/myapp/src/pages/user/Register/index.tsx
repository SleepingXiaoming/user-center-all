import Footer from '@/components/Footer';
import {register} from '@/services/ant-design-pro/api';
import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {SYSTEM_LOGO} from "@/constants";
import {
  LoginForm,
  ProFormText,
} from '@ant-design/pro-components';
import {message, Tabs} from 'antd';
import React, {useState} from 'react';
import {history} from 'umi';
import styles from './index.less';


const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');


  // 表单提交
  const handleSubmit = async (values: API.RegisterParams) => {
    // 校验
    const {userPassword, checkPassword} = values;

    if (userPassword !== checkPassword) {
      message.error('两次输入的密码不一致，请查证');
      return;
    }

    try {
      // 注册
      const id = await register(values);
      // const res = await register(values);
      if (id > 0) {
        // if (res.code === 0 && res.data > 0) {
        const defaultLoginSuccessMessage = '注册成功！';
        message.success(defaultLoginSuccessMessage);
        /** 此方法会跳转到 redirect 参数所在的位置 */

        if (!history) return;
        const {query} = history.location;
        const {redirect} = query as {
          redirect: string;
        };
        //
        history.push(redirect ? ('/user/login?redirect=' + redirect) : '/');
        return;
      }
    } catch (error: any) {
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          submitter={{
            searchConfig: {
              submitText: '注册'
            }
          }}
          logo={<img alt="logo" src={SYSTEM_LOGO}/>}
          title="用户中心"
          subTitle={<a href={"http://www.baidu.com"} target={"_blank"} rel="noreferrer">基于Spring Boot 和 Umi
            实现的用户管理中心</a>}
          initialValues={{
            autoLogin: true,
          }}

          // 点击注册按钮
          onFinish={async (values) => {
            await handleSubmit(values as API.RegisterParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码注册'}/>
          </Tabs>

          {type === 'account' && (
            <>
              <ProFormText
                name="email"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入邮箱'}
                rules={[
                  {
                    required: true,
                    message: '注册邮箱是必填项！',
                  },
                ]}
              />
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入账户'}
                rules={[
                  {
                    required: true,
                    message: '注册账户是必填项！',
                  },
                ]}
              />
              <ProFormText
                name="studentNumber"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入学号'}
                rules={[
                  {
                    required: true,
                    message: '学号是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                  {
                    min: 8,
                    type: "string",
                    message: "密码长度不能小于8",
                  },
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请再次输入密码'}
                rules={[
                  {
                    required: true,
                    message: '确认密码是必填项！',
                  },
                  {
                    min: 8,
                    type: "string",
                    message: "密码长度不能小于8",
                  },
                ]}
              />
            </>
          )}

          {/*{status === 'error' && loginType === 'mobile' && <LoginMessage content="验证码错误"/>}*/}

          <div
            style={{
              marginBottom: 24,
            }}
          >
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  )
    ;
};

export default Register;
