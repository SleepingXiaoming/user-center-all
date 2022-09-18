import Footer from '@/components/Footer';
import {login} from '@/services/ant-design-pro/api';
import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {CONTACT_DETAILS, SYSTEM_LOGO} from "@/constants";
import {
  LoginForm,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import {Alert, message, Tabs} from 'antd';
import React, {useState} from 'react';
import {history, Link, useModel} from 'umi';
import styles from './index.less';


const LoginMessage: React.FC<{
  content: string;
}> = ({content}) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

const Login: React.FC = () => {
  const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('account');
  const {initialState, setInitialState} = useModel('@@initialState');

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();

    if (userInfo) {
      await setInitialState((s) => ({...s, currentUser: userInfo}));
    }
  };


  const handleSubmit = async (values: API.LoginParams) => {
    try {
      // 登录
      const user = await login({...values, type});

      if (user) {
        const defaultLoginSuccessMessage = '登录成功！';
        message.success(defaultLoginSuccessMessage);
        await fetchUserInfo();
        /** 此方法会跳转到 redirect 参数所在的位置 */

        if (!history) return;
        const {query} = history.location;
        const {redirect} = query as {
          redirect: string;
        };
        history.push(redirect || '/');
        return;
      }


      setUserLoginState(user);
    } catch (error) {
      const defaultLoginFailureMessage = '登录失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };

  const {status, type: loginType} = userLoginState;
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src={SYSTEM_LOGO}/>}// /logo.svg
          title="用户中心"
          subTitle={<a href={"http://www.baidu.com"} target={"_blank"} rel="noreferrer">基于Spring Boot 和 Umi
            实现的用户管理中心</a>}
          initialValues={{
            autoLogin: true,
          }}

          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码登录'}/>
          </Tabs>

          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的登录账户和密码'}/>
          )}
          {type === 'account' && (
            <>
              <ProFormText
                name="loginInWay"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入账户或者邮箱'}
                rules={[
                  {
                    required: true,
                    message: '登录账户是必填项！',
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
            </>
          )}

          {status === 'error' && loginType === 'mobile' && <LoginMessage content="验证码错误"/>}

          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            <br/>
            <Link to={"/user/register"}>新用户注册</Link>
            <a
              style={{
                float: 'right',
              }}
              href={CONTACT_DETAILS}
              target={"_blank"}
              rel="noreferrer"
            >
              忘记密码
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  )
    ;
};

export default Login;
