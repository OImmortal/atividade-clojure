(ns integrador.integrador)

(defn clenup-system []
    (Thread/sleep 1000) ; Wait for 2 seconds before restarting
    (print (str (char 27) "[2J")) ; ANSI escape code to clear screen
    (print (str (char 27) "[;H")) ; Move cursor to top-left
    (flush)
)

(def alunos [])

(defn -main []
    

    (loop []
        (println "=== MENU PRINCIPAL ===")
        (println "1 - Cadastrar Alunos")
        (println "2 - Relatório de Notas")
        (println "3 - Estatísticas Gerais")
        (println "0 - Sair")
        (print "Escolha uma opção: ") (flush)
        (let [opcao (Integer/parseInt (read-line))]

            (cond
                (= opcao 1) (do 
                    (loop []
                        (clenup-system)
                        (println "=== CADASTRO DE ALUNOS ===")
                        (print "Nome do aluno (ou deixe em branco para voltar ao menu): ") (flush)
                        (let [nome (read-line)]
                            (if (= nome "")
                                (clenup-system)
                                (do
                                    (print "Informe a nota do aluno: ") (flush)
                                    (let [nota (Double/parseDouble (read-line))]
                                        (def aluno {:nome nome :nota nota})
                                        (def alunos (conj alunos aluno))
                                        (println (str "Aluno " nome " coma a nota " nota " cadastrado com sucesso!"))
                                        (clenup-system)
                                        (recur)
                                    )
                                )
                            )
                        )
                    )
                    (clenup-system)
                    (recur)
                )
                (= opcao 2) (do 
                    (clenup-system)
                    (println "=== RELATORIO DE ALUNOS APROVADOS ===")

                    (def alunos (map #(assoc % :status (if (>= (:nota %) 60) "Aprovado" "Reprovado")) alunos))

                    (doseq [aluno alunos]
                        (when (= (:status aluno) "Aprovado")
                            (println (str "Aluno: " (:nome aluno) " - Nota: " (:nota aluno)))
                        )
                    ) 

                    (let [alunos-aprovados (filter #(= (:status %) "Aprovado") alunos)
                          soma-notas (reduce + 0 (map :nota alunos-aprovados))
                          quantidade-aprovados (count alunos-aprovados)] 
                        (println (str "Soma das notas: " soma-notas))
                        (if (> quantidade-aprovados 0)
                            (println (str "Média da Turma: " (/ soma-notas quantidade-aprovados)))
                            (println "Nenhum aluno aprovado para calcular a média"))
                    )

                          
                    
                    (Thread/sleep 5000)
                    (clenup-system)
                    (recur)
                )
                (= opcao 3) (do 
                    (clenup-system)
                    (println "=== RELATORIO DE ALUNOS ===")

                    (doseq [aluno alunos]
                        (println (str "Aluno: " (:nome aluno) " - Nota: " (:nota aluno)))
                    ) 

                    (println (str "Total de alunos cadastrados: " (count alunos)))

                    (let [alunos-aprovados (filter #(= (:status %) "Aprovado") alunos) soma-notas (reduce + 0 (map :nota alunos)) quantidade-aprovados (count alunos-aprovados)] 
                        (println (str "Quantidade de alunos aprovados: " quantidade-aprovados))
                        (println (str "Quantidade de alunos reprovado: " (- (count alunos) quantidade-aprovados)))
                        (if (pos? (count alunos))
                            (let [notas (map :nota alunos)
                                        maior (apply max notas)
                                        menor (apply min notas)]
                                (println (str "Maior nota: " maior))
                                (println (str "Menor nota: " menor)))
                            (println "Nenhum aluno cadastrado para mostrar maior/menor nota"))
                        (if (> quantidade-aprovados 0)
                            (println (str "Média da Turma: " (/ soma-notas quantidade-aprovados)))
                            (println "Nenhum aluno aprovado para calcular a média"))
                    )

                          
                    
                    (Thread/sleep 5000)
                    (clenup-system)
                    (recur)
                )
                (= opcao 0) (do
                    (println "Saindo do programa. Até mais!")   
                )
                :else (do
                    (println "Opção inválida. Tente novamente.")
                    (clenup-system)
                    (recur)
                )
            )

        )
    )




)