package net.eltex.aptprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.tools.Diagnostic.Kind;

import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.ClassSymbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type.ClassType;

@SupportedAnnotationTypes(value = { "net.eltex.ann.MustOverride" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class MustOverrideProcessor extends AbstractProcessor {

	@Override
	public void init(ProcessingEnvironment env) {
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment env) {

		boolean process = true;

		mustOverridMap = new HashMap<ClassSymbol, ArrayList<MethodSymbol>>();

		for (TypeElement ann : annotations) {

			Set<? extends Element> e2s = env.getElementsAnnotatedWith(ann);
			for (Element element : e2s) {
				if (element instanceof MethodSymbol) {
					createMustOverride((MethodSymbol) element);
				}
			}
		}

		Set<? extends Element> elements = env.getRootElements();
		for (Element element : elements) {
			if (element instanceof ClassSymbol) {
				if (check((ClassSymbol) element, (ClassSymbol) element) == false) {
					process = false;
				}
			} else {
				continue;
			}

		}

		return process;
	}

	private void createMustOverride(MethodSymbol methodSymbol) {
		ClassSymbol classSymbol = (ClassSymbol) methodSymbol.owner;
		addMustOverride(classSymbol, methodSymbol);
	}

	private void addMustOverride(ClassSymbol classSymbol,
			MethodSymbol methodSymbol) {
		ArrayList<MethodSymbol> symbols = new ArrayList<Symbol.MethodSymbol>();

		if (mustOverridMap.containsKey(classSymbol) == false) {
			mustOverridMap.put(classSymbol, symbols);
		}

		symbols = mustOverridMap.get(classSymbol);
		symbols.add(methodSymbol);

	}

	private boolean check(ClassSymbol checkClassSymbol, ClassSymbol classSymbol) {

		boolean check = true;

		if (mustOverridMap.containsKey(classSymbol) == true) {
			ArrayList<MethodSymbol> symbols = mustOverridMap.get(classSymbol);
			if (symbols != null) {
				for (MethodSymbol methodSymbol : symbols) {
					if (existMethod(checkClassSymbol, methodSymbol) == false) {
						check = false;
						messager.printMessage(Kind.ERROR, "Not exist method "
								+ methodSymbol + ". Annotation @MustOverride.",
								checkClassSymbol);
					}
				}
			}
		}

		ClassSymbol parentClassSymbol = getParent(classSymbol);
		if (parentClassSymbol != null) {
			return check && check(checkClassSymbol, parentClassSymbol);
		}

		return check;
	}

	private ClassSymbol getParent(ClassSymbol classSymbol) {

		if (classSymbol.asType() instanceof ClassType) {
			ClassType type = (ClassType) classSymbol.asType();

			if (type.supertype_field instanceof NoType) {
				return null;
			}

			if (type.supertype_field instanceof ClassType
					&& type.supertype_field.asElement() instanceof ClassSymbol) {
				return (ClassSymbol) type.supertype_field.asElement();
			}

		}

		return null;
	}

	private boolean existMethod(ClassSymbol classSymbol,
			MethodSymbol methodSymbol) {

		Scope scope = classSymbol.members();
		for (Symbol symbol : scope.getElements()) {
			if (symbol instanceof MethodSymbol
					&& methodSymbol.toString().equals(symbol.toString())) {
				return true;
			}
		}

		return false;
	}

	private Messager messager;
	private Map<ClassSymbol, ArrayList<MethodSymbol>> mustOverridMap;
}