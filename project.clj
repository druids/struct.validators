(defproject struct.validators "0.0.0"
  :description "Additional validators for Struct library"
  :url "https://github.com/druids/struct.validators"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}


  :profiles {:dev {:plugins [[lein-cloverage "1.0.10"]
                             [lein-kibit "0.1.6"]
                             [jonase/eastwood "0.2.5"]]

                   :dependencies [[org.clojure/clojure "1.9.0"]
                                  [org.clojure/clojurescript "1.10.238"]
                                  [funcool/struct "1.2.0"]]}
             :cljs {:plugins [[lein-cljsbuild "1.1.7"]
                              [lein-doo "0.1.10"]]
                    :doo {:build "test"}
                    :cljsbuild {:builds
                                {:test {:source-paths ["src" "test"]
                                        :compiler {:main struct.runner
                                                   :output-to "target/test/core.js"
                                                   :optimizations :none
                                                   :source-map true
                                                   :pretty-print true
                                                   ;; workaround for running lein doo with latest CLJS, see
                                                   ;; https://github.com/bensu/doo/pull/141}}}}
                                                   :process-shim false}}}}}}
  :aliases {"cljs-tests" ["with-profile" "cljs" "doo" "phantom" "once"]
            "cljs-auto" ["with-profile" "cljs" "cljsbuild" "auto"]
            "cljs-once" ["with-profile" "cljs" "cljsbuild" "once"]})
