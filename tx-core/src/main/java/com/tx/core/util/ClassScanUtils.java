/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 类扫描工具<br/>
 *    注：在使用该类时，如果出现metadata加载后于具体实现期间，则需要添加dependOn的注解以实现优先加载的效果<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ClassScanUtils
        implements ResourceLoaderAware, EnvironmentAware, InitializingBean {
    
    /** 日志记录器 */
    protected static Logger logger = LoggerFactory
            .getLogger(ClassScanUtils.class);
    
    private static ResourceLoader resourceLoader;
    
    private static Environment environment;
    
    private static MetadataReaderFactory metadataReaderFactory;
    
    private static boolean initialized = false;
    
    /**
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        ClassScanUtils.environment = environment;
    }
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        ClassScanUtils.resourceLoader = resourceLoader;
        
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //生成MetadataReaderFactory
        ClassScanUtils.metadataReaderFactory = new CachingMetadataReaderFactory(
                resourceLoader);
        
        ClassScanUtils.initialized = true;
    }
    
    /**
     * 根据注解以及根包，查找类集合
     * <功能详细描述>
     * @param annotation
     * @param packageNames
     * @return [参数说明]
     * 
     * @return Set<Class<? extends Class<?>>> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static Set<Class<?>> scanByAnnotation(
            Class<? extends Annotation> annotationType,
            String... basePackages) {
        Environment environment = ClassScanUtils.environment;
        MetadataReaderFactory metadataReaderFactory = ClassScanUtils.metadataReaderFactory;
        ResourceLoader resourceLoader = ClassScanUtils.resourceLoader;
        if (!ClassScanUtils.initialized) {
            environment = new StandardEnvironment();
            resourceLoader = new DefaultResourceLoader();
            metadataReaderFactory = new CachingMetadataReaderFactory(
                    resourceLoader);
        }
        
        AssertUtils.notNull(annotationType, "annotationType is null.");
        AssertUtils.notEmpty(basePackages, "basePackages is null.");
        
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false, environment);
        provider.setMetadataReaderFactory(metadataReaderFactory);
        provider.addIncludeFilter(
                new AnnotationTypeFilter(annotationType, true, true));
        
        Set<Class<?>> types = new HashSet<Class<?>>();
        for (String basePackage : basePackages) {
            if (StringUtils.isBlank(basePackage)) {
                continue;
            }
            for (BeanDefinition definition : provider
                    .findCandidateComponents(basePackage.trim())) {
                try {
                    types.add(ClassUtils.forName(definition.getBeanClassName(),
                            resourceLoader == null ? null
                                    : resourceLoader.getClassLoader()));
                } catch (ClassNotFoundException o_O) {
                    throw new IllegalStateException(o_O);
                }
            }
        }
        
        return types;
    }
    
    /**
     * 根据父类以及根包，查找类集合
     * <功能详细描述>
     * @param parent
     * @param packageNames
     * @return [参数说明]
     * 
     * @return Set<Class<? extends Class<?>>> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> scanByParentClass(
            Class<T> targetType, String... basePackages) {
        Environment environment = ClassScanUtils.environment;
        MetadataReaderFactory metadataReaderFactory = ClassScanUtils.metadataReaderFactory;
        ResourceLoader resourceLoader = ClassScanUtils.resourceLoader;
        if (!ClassScanUtils.initialized) {
            environment = new StandardEnvironment();
            resourceLoader = new DefaultResourceLoader();
            metadataReaderFactory = new CachingMetadataReaderFactory(
                    resourceLoader);
        }
        
        AssertUtils.notNull(targetType, "targetType is null.");
        AssertUtils.notEmpty(basePackages, "basePackages is null.");
        
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false, environment);
        provider.setMetadataReaderFactory(metadataReaderFactory);
        provider.addIncludeFilter(new AssignableTypeFilter(targetType));
        
        Set<Class<? extends T>> types = new HashSet<Class<? extends T>>();
        for (String basePackage : basePackages) {
            if (StringUtils.isBlank(basePackage)) {
                continue;
            }
            
            for (BeanDefinition definition : provider
                    .findCandidateComponents(basePackage.trim())) {
                try {
                    types.add((Class<? extends T>) ClassUtils.forName(
                            definition.getBeanClassName(),
                            resourceLoader == null ? null
                                    : resourceLoader.getClassLoader()));
                } catch (ClassNotFoundException o_O) {
                    throw new IllegalStateException(o_O);
                }
            }
        }
        
        return types;
    }
    
    //    /**
    //      * 根据注解以及根包，查找类集合
    //      * <功能详细描述>
    //      * @param annotation
    //      * @param packageNames
    //      * @return [参数说明]
    //      * 
    //      * @return Set<Class<? extends Class<?>>> [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public static <T> Set<Class<? extends T>> scanByAnnotation(
    //            Class<? extends Annotation> annotation, String... packageNames) {
    //        ResolverUtil<T> resolverUtil = new ResolverUtil<T>();
    //        resolverUtil.findAnnotated(annotation, packageNames);
    //        return resolverUtil.getClasses();
    //    }
    //    
    //    /**
    //      * 根据父类以及根包，查找类集合
    //      * <功能详细描述>
    //      * @param parent
    //      * @param packageNames
    //      * @return [参数说明]
    //      * 
    //      * @return Set<Class<? extends Class<?>>> [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public static <T> Set<Class<? extends T>> scanByParentClass(Class<T> parent,
    //            String... packageNames) {
    //        ResolverUtil<T> resolverUtil = new ResolverUtil<T>();
    //        resolverUtil.findImplementations(parent, packageNames);
    //        
    //        return resolverUtil.getClasses();
    //    }
    //    /**
    //     * 
    //     * <功能简述>
    //     * <功能详细描述>
    //     * 
    //     * @author  Administrator
    //     * @version  [版本号, 2018年5月3日]
    //     * @see  [相关类/方法]
    //     * @since  [产品/模块版本]
    //     */
    //    public static class ResolverUtil<T> {
    //        
    //        public interface Test {
    //            
    //            boolean matches(Class<?> type);
    //        }
    //        
    //        public static class IsA implements Test {
    //            
    //            private Class<?> parent;
    //            
    //            public IsA(Class<?> parentType) {
    //                this.parent = parentType;
    //            }
    //            
    //            @Override
    //            public boolean matches(Class<?> type) {
    //                return type != null && parent.isAssignableFrom(type);
    //            }
    //            
    //            @Override
    //            public String toString() {
    //                return "is assignable to " + parent.getSimpleName();
    //            }
    //        }
    //        
    //        public static class AnnotatedWith implements Test {
    //            
    //            private Class<? extends Annotation> annotation;
    //            
    //            public AnnotatedWith(Class<? extends Annotation> annotation) {
    //                this.annotation = annotation;
    //            }
    //            
    //            @Override
    //            public boolean matches(Class<?> type) {
    //                return type != null && type.isAnnotationPresent(annotation);
    //            }
    //            
    //            @Override
    //            public String toString() {
    //                return "annotated with @" + annotation.getSimpleName();
    //            }
    //        }
    //        
    //        private Set<Class<? extends T>> matches = new HashSet<Class<? extends T>>();
    //        
    //        private ClassLoader classloader;
    //        
    //        public Set<Class<? extends T>> getClasses() {
    //            return matches;
    //        }
    //        
    //        public ClassLoader getClassLoader() {
    //            return classloader == null
    //                    ? Thread.currentThread().getContextClassLoader()
    //                    : classloader;
    //        }
    //        
    //        public void setClassLoader(ClassLoader classloader) {
    //            this.classloader = classloader;
    //        }
    //        
    //        public ResolverUtil<T> findImplementations(Class<?> parent,
    //                String... packageNames) {
    //            if (packageNames == null) {
    //                return this;
    //            }
    //            
    //            Test test = new IsA(parent);
    //            for (String pkg : packageNames) {
    //                find(test, pkg);
    //            }
    //            
    //            return this;
    //        }
    //        
    //        /**
    //         * Attempts to discover classes that are annotated with the annotation. Accumulated
    //         * classes can be accessed by calling {@link #getClasses()}.
    //         *
    //         * @param annotation the annotation that should be present on matching classes
    //         * @param packageNames one or more package names to scan (including subpackages) for classes
    //         */
    //        public ResolverUtil<T> findAnnotated(
    //                Class<? extends Annotation> annotation,
    //                String... packageNames) {
    //            if (packageNames == null) {
    //                return this;
    //            }
    //            
    //            Test test = new AnnotatedWith(annotation);
    //            for (String pkg : packageNames) {
    //                find(test, pkg);
    //            }
    //            
    //            return this;
    //        }
    //        
    //        public ResolverUtil<T> find(Test test, String packageName) {
    //            String path = getPackagePath(packageName);
    //            
    //            try {
    //                //VFS.getInstance();
    //                VFS vfs = VFS.getInstance();//new SpringVFS();
    //                List<String> children = vfs.list(path);
    //                for (String child : children) {
    //                    if (child.endsWith(".class")) {
    //                        addIfMatching(test, child);
    //                    }
    //                }
    //            } catch (IOException ioe) {
    //                logger.error("Could not read package: " + packageName, ioe);
    //            }
    //            
    //            return this;
    //        }
    //        
    //        /**
    //         * Converts a Java package name to a path that can be looked up with a call to
    //         * {@link ClassLoader#getResources(String)}.
    //         * 
    //         * @param packageName The Java package name to convert to a path
    //         */
    //        protected String getPackagePath(String packageName) {
    //            return packageName == null ? null : packageName.replace('.', '/');
    //        }
    //        
    //        /**
    //         * Add the class designated by the fully qualified class name provided to the set of
    //         * resolved classes if and only if it is approved by the Test supplied.
    //         *
    //         * @param test the test used to determine if the class matches
    //         * @param fqn the fully qualified name of a class
    //         */
    //        @SuppressWarnings("unchecked")
    //        protected void addIfMatching(Test test, String fqn) {
    //            try {
    //                String externalName = fqn.substring(0, fqn.indexOf('.'))
    //                        .replace('/', '.');
    //                ClassLoader loader = getClassLoader();
    //                if (logger.isDebugEnabled()) {
    //                    logger.debug("Checking to see if class " + externalName
    //                            + " matches criteria [" + test + "]");
    //                }
    //                
    //                Class<?> type = loader.loadClass(externalName);
    //                if (test.matches(type)) {
    //                    matches.add((Class<T>) type);
    //                }
    //            } catch (Throwable t) {
    //                logger.warn("Could not examine class '" + fqn + "'"
    //                        + " due to a " + t.getClass().getName()
    //                        + " with message: " + t.getMessage());
    //            }
    //        }
    //    }
    //    
    //    public static class SpringVFS extends VFS {
    //        
    //        private final ResourcePatternResolver resourceResolver;
    //        
    //        public SpringVFS() {
    //            this.resourceResolver = new PathMatchingResourcePatternResolver(
    //                    getClass().getClassLoader());
    //        }
    //        
    //        @Override
    //        public boolean isValid() {
    //            return true;
    //        }
    //        
    //        @Override
    //        protected List<String> list(URL url, String path) throws IOException {
    //            Resource[] resources = resourceResolver
    //                    .getResources("classpath*:" + path + "/**/*.class");
    //            List<String> resourcePaths = new ArrayList<String>();
    //            for (Resource resource : resources) {
    //                resourcePaths
    //                        .add(preserveSubpackageName(resource.getURI(), path));
    //            }
    //            return resourcePaths;
    //        }
    //        
    //        private static String preserveSubpackageName(final URI uri,
    //                final String rootPath) {
    //            final String uriStr = uri.toString();
    //            final int start = uriStr.indexOf(rootPath);
    //            return uriStr.substring(start);
    //        }
    //        
    //    }
    
}
