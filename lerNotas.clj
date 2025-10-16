(ns atividade01.lerNotas)

(def notas (list))
(def nomes (list))

(defn lerNotas []
    (loop [i 0]
        (when (< i 2)
            (print "Digite o nome: ") (flush)

            (let [nome (read-line)]
                (def nomes (conj nomes nome))
            )

            (print "Digite a nota: ") (flush)
            
            (let [num (Integer/parseInt (read-line))]
                (if (or (< num 0) (> num 100))
                    (do 
                        (println "Nota invalida! Tente novamente.")
                        (def nomes (drop-last nomes))
                        (recur i))
                    (do
                        (def notas (conj notas num))
                        (recur (inc i)))))
        )
    )
)

(defn -main []

    (lerNotas)
    
    (loop [j 0]
        (when (< j (count nomes))
            (println "Nome: " (nth nomes j) " - Nota: " 
                (cond 
                    (>= (nth notas j) 90) "A"
                    (>= (nth notas j) 80) "B"
                    (>= (nth notas j) 70) "C"
                    (>= (nth notas j) 60) "D"
                    :else "F"
                )
            )
            (recur (inc j))
        )
    )

    (let [media (/ (reduce + notas) (count notas))]
        (println "Média geral:" media))

)