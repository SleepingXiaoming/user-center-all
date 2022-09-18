import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';

const Footer: React.FC = () => {
  const defaultMessage = 'XiaoMing出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'LeetCode',
          title: 'LeetCode',
          href: 'https://leetcode.cn/u/sleepingxiaoming/',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined />XiaoMing</>,
          href: 'https://github.com/SleepingXiaoming',
          blankTarget: true,
        },
        {
          key: 'CSDN',
          title: 'CSDN',
          href: 'https://blog.csdn.net/qq_46176960',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
