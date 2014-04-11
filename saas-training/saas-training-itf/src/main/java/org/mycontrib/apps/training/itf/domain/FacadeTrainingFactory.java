package org.mycontrib.apps.training.itf.domain;

import java.lang.reflect.Constructor;

public class FacadeTrainingFactory {
	
	private static FacadeTraining singleton=null;
	
	public static final String DEFAULT_IMPL_PACKAGE = "org.mycontrib.apps.training.impl.domain";
	public static final String DEFAULT_LOCALE_FACADE_CLASS_NAME="FacadeTrainingImpl";
	public static final String DEFAULT_DELEGATE_FACADE_CLASS_NAME="FacadeTrainingDelegate";
	
	private static String implFacadePackage = DEFAULT_IMPL_PACKAGE;
	private static String localeFacadeClassName=DEFAULT_LOCALE_FACADE_CLASS_NAME;
	private static String delegateFacadeClassName=DEFAULT_DELEGATE_FACADE_CLASS_NAME;
	
	
	

    //with default configuration (ex: default Spring config)
	public static FacadeTraining getInstance(){

		if(singleton==null){
			try {
				//premier essai : implementation locale
				singleton=(FacadeTraining) Class.forName(implFacadePackage + "." + localeFacadeClassName).newInstance();
			} catch (Exception e) {
				System.err.println(e.getMessage() + " not found or not created");
			}						
		}
		if(singleton==null){
			try {
				//deuxieme essai :  business delegate 
				singleton=(FacadeTraining) Class.forName(implFacadePackage + "." + delegateFacadeClassName).newInstance();
			} catch (Exception e) {
				System.err.println(e.getMessage() + " not found or not created");
			}						
		}
		return singleton;
	}
	

	public static FacadeTraining getInstanceFromContext(Object /* as (Spring)ApplicationContext */ ctx){

		Class contextClass = null; //ServletContext.class or ApplicationContext.class ?
		try{
			Class ctxExcactClass = ctx.getClass();
			System.out.println("ctx in FacadeMiniBankFactory.getInstanceFromContext() of exact class: "
					+ctxExcactClass.getName() );
			Class springApplicationContextClass = Class.forName("org.springframework.context.ApplicationContext");
				 if(springApplicationContextClass.isAssignableFrom(ctxExcactClass)) 
					 contextClass = springApplicationContextClass; 
			System.out.println("FacadeTrainingFactory - creation facade via contexte de type " + contextClass.getName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}	
		
		if(singleton==null){
			try {
				//premier essai : implementation locale
				Constructor constructor  = Class.forName(implFacadePackage + "." + localeFacadeClassName).getConstructor(contextClass);				
				singleton=(FacadeTraining) constructor.newInstance(ctx);
			} catch (Exception e) {
				System.err.println(e.getMessage() + " not found or not created");
			}						
		}
		if(singleton==null){
			try {
				//deuxieme essai :  business delegate 
				Constructor constructor  = Class.forName(implFacadePackage + "." + delegateFacadeClassName).getConstructor(contextClass);
				singleton=(FacadeTraining) constructor.newInstance(ctx);
			} catch (Exception e) {
				System.err.println(e.getMessage() + " not found or not created");
			}						
		}
		return singleton;
	}
	
	

	public static String getImplFacadePackage() {
		return implFacadePackage;
	}


	public static void setImplFacadePackage(String implFacadePackage) {
		FacadeTrainingFactory.implFacadePackage = implFacadePackage;
	}


	public static String getLocaleFacadeClassName() {
		return localeFacadeClassName;
	}


	public static void setLocaleFacadeClassName(String localeFacadeClassName) {
		FacadeTrainingFactory.localeFacadeClassName = localeFacadeClassName;
	}


	public static String getDelegateFacadeClassName() {
		return delegateFacadeClassName;
	}


	public static void setDelegateFacadeClassName(String delegateFacadeClassName) {
		FacadeTrainingFactory.delegateFacadeClassName = delegateFacadeClassName;
	}

	
}
