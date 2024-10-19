package com.itranlin.hexagon.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.assembly.mojos.SingleAssemblyMojo;

/**
 * 将插件打包成Hexagon能使用的插件包.
 */
@Mojo(name = "builder", defaultPhase = LifecyclePhase.PACKAGE)
@SuppressWarnings("unused")
public class PackageMojo extends SingleAssemblyMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.setDescriptorRefs(new String[]{"plugin"});
        super.execute();
    }
}
