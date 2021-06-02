.PHONY: run ancient clean uberjar
run:
	clj -M:cider

ancient:
	clj -M:cider:ancient

clean:
	rm -rf target classes .cpcache

uberjar: target/ecomspark.jar

define CLASS_BUILDER
(binding [clojure.core/*compiler-options*
                        {:direct-linking true
                         :elide-meta [:doc :file :line :added]}]
	(compile (symbol "ecomspark.main")))
endef
export CLASS_BUILDER

classes: ./src/ecomspark deps.edn
	mkdir -p classes
	clj -M -e "$$CLASS_BUILDER"

target/ecomspark.jar: classes ./src/ecomspark deps.edn
	clojure -M:uberjar --main-class ecomspark.main
