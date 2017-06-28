## Java调用Shell编程

    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;

    import org.apache.commons.exec.CommandLine;
    import org.apache.commons.exec.DefaultExecutor;
    import org.apache.commons.exec.ExecuteException;
    import org.apache.commons.exec.Executor;
    import org.apache.log4j.Logger;

    public final class LinuxShellExecutor
    {
        private static final Logger log = Logger.getLogger(LinuxShellExecutor.class);

        /**
         * @param srcDir
         *            源文件或目录
         * @param targetDir
         *            目标文件或目录
         * @return true:移动成功，false:移动失败
         */
        public static boolean mv(String srcDir, String targetDir)
        {
            CommandLine cmd = new CommandLine("mv");
            cmd.addArgument("-f"); // 直接覆盖现有的文件或目录

            cmd.addArgument("${srcDir}"); // 源目录
            cmd.addArgument("${targetDir}"); // 目标目录

            Map<String, String> params = new HashMap<String, String>(2);
            params.put("srcDir", srcDir);
            params.put("targetDir", targetDir);

            cmd.setSubstitutionMap(params);

            return exec(cmd, 0);
        }

        /**
         * 删除文件或目录
         * @param target
         *            目标文件或目录
         * @return true:删除成功，false:删除失败
         */
        public static boolean rm(String target)
        {
            CommandLine cmd = new CommandLine("rm");
            cmd.addArgument("-f"); // 强制删除
            cmd.addArgument("-r"); // 递归处理

            cmd.addArgument("${target}");

            Map<String, String> params = new HashMap<String, String>(1);
            params.put("target", target);

            cmd.setSubstitutionMap(params);

            return exec(cmd, 0);
        }

        /**
         * 复制文件或目录
         * @param srcDir
         *            源文件或目录
         * @param targetDir
         *            目标文件或目录
         * @return true:复制成功，false:复制失败
         */
        public static boolean cp(String srcDir, String targetDir)
        {
            CommandLine cmd = new CommandLine("cp");
            cmd.addArgument("-f"); // 强行复制
            cmd.addArgument("-r"); // 递归处理

            cmd.addArgument("${srcDir}");
            cmd.addArgument("${targetDir}");

            Map<String, String> params = new HashMap<String, String>(2);
            params.put("srcDir", srcDir);
            params.put("targetDir", targetDir);

            cmd.setSubstitutionMap(params);

            return exec(cmd, 0);
        }

        /**
         * 解压文件。
         * @param zipPath
         *            被解压文件绝对路径
         * @param unzipPath
         *            解压文件保存绝对路径
         * @return true:解压成功，false:解压失败
         */
        public static boolean unzip(String zipPath, String unzipPath)
        {
            CommandLine cmd = new CommandLine("unzip");

            cmd.addArgument("${zipPath}");
            cmd.addArgument("-d");
            cmd.addArgument("${unzipPath}");

            Map<String, String> params = new HashMap<String, String>(2);
            params.put("zipPath", zipPath);
            params.put("unzipPath", unzipPath);

            cmd.setSubstitutionMap(params);

            return exec(cmd, 0);
        }

        /**
         * 执行字符串表示的命令
         * @param cmd
         *            要执行的命令
         * @param expecpedRetCode
         *            预期的返回值
         * @return 返回是否成功
         */
        public static boolean exec(String cmd, int expecpedRetCode)
        {
            CommandLine cmdl = CommandLine.parse(cmd);

            return exec(cmdl, 0);
        }

        /**
         * 执行指定的命令
         * @param cmd
         *            要执行的命令
         * @param expecpedRetCode
         *            预期的执行返回值
         * @return true:成功执行，false:执行失败
         */
        private static boolean exec(CommandLine cmd, int expecpedRetCode)
        {
            Executor executor = new DefaultExecutor();

            try
            {
                int retCode = executor.execute(cmd);

                return retCode == expecpedRetCode;
            }
            catch (ExecuteException e)
            {
                log.error("execute comman:" + cmd + " faile!", e);
            }
            catch (IOException e)
            {
                log.error(
                        "execute comman:" + cmd + " exited with an IO exception!",
                        e);
            }

            return false;
        }

		public static void main(String[] args)
        {
        	String cmd = String.format("%s %s %s %s %s %s", "nohup " + shellpath,
                    filePath, tableName, systag, billtype, "&");
        	//解析成Shell CMD
            CommandLine cmdl = CommandLine.parse(cmd);
        }
    }
