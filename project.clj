(defproject automat "0.2.1-SNAPSHOT"
  :description ""
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[rhizome "0.2.5"]
                 [primitive-math "0.1.4"]
                 [potemkin "0.4.3"]
                 [proteus "0.1.6"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "1.7.228"]
                                  [org.clojure/test.check "0.9.0"]
                                  [criterium "0.4.3"]
                                  [codox-md "0.2.0" :exclusions [org.clojure/clojure]]]
                   :prep-tasks [["cljx" "once"] "javac" "compile"]
                   :auto-clean false
                   :aliases {"clean-test" ["do" "clean," "cljx" "once," "test," "cljsbuild" "test"]
                             "clean-build" ["do" "clean," "cljx" "once," "cljsbuild" "once"]}}}
  :test-selectors {:default #(every? (complement #{:stress :benchmark}) (keys %))
                   :stress :stress
                   :benchmark :benchmark}
  :global-vars {*warn-on-reflection* true}
  :jvm-opts ^:replace ["-server" "-Xmx2g"]
  :java-source-paths ["src"]
  :jar-exclusions [#"\.cljx|\.DS_Store"]
  :source-paths ["src" "target/src" "target/classes"]
  :test-paths ["test" "target/test"]
  :plugins [[codox "0.9.1"]
            [com.cemerick/clojurescript.test "0.3.3"]
            [com.keminglabs/cljx "0.5.0"]
            [lein-cljsbuild "1.1.2"]]
  :cljx {:builds [{:source-paths ["src"]
                   :output-path "target/src"
                   :rules :clj}
                  {:source-paths ["src"]
                   :output-path "target/src"
                   :rules :cljs}
                  {:source-paths ["test"]
                   :output-path "target/test"
                   :rules :clj}
                  {:source-paths ["test"]
                   :output-path "target/test"
                   :rules :cljs}]}
  :cljsbuild {:builds [{:source-paths ["src" "test"]
                        :compiler {:output-to "target/test.js"
                                   :source-map "target/test.js.map"
                                   :output-dir "target/js"
                                   :optimizations :advanced
                                   :parallel-build true}}]
              :test-commands {"phantom" ["phantomjs" :runner "target/test.js"]}}
  :codox {:writer codox-md.writer/write-docs
          :include [automat.core automat.viz]})
